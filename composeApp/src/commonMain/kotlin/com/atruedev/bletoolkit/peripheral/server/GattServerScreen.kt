package com.atruedev.bletoolkit.peripheral.server

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun GattServerScreen(viewModel: GattServerViewModel) {
    val state by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text("GATT Server", style = MaterialTheme.typography.titleSmall)
            when (state.state) {
                ServerState.Stopped -> Button(onClick = viewModel::startServer) { Text("Start") }
                ServerState.Running -> OutlinedButton(onClick = viewModel::stopServer) { Text("Stop") }
                is ServerState.Error -> Button(onClick = viewModel::startServer) { Text("Retry") }
            }
        }

        if (state.error != null) {
            Text(state.error!!, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        state.services.forEachIndexed { si, service ->
            ServiceConfigCard(
                service = service,
                serviceIndex = si,
                onUuidChange = { viewModel.updateServiceUuid(si, it) },
                onAddChar = { viewModel.addCharacteristic(si) },
                onRemove = { viewModel.removeService(si) },
                onCharUuidChange = { ci, uuid -> viewModel.updateCharUuid(si, ci, uuid) },
                onToggleProperty = { ci, prop -> viewModel.toggleCharProperty(si, ci, prop) },
                enabled = state.state is ServerState.Stopped,
            )
        }

        if (state.state is ServerState.Stopped) {
            TextButton(onClick = viewModel::addService) { Text("+ Add Service") }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ServiceConfigCard(
    service: ServerServiceConfig,
    serviceIndex: Int,
    onUuidChange: (String) -> Unit,
    onAddChar: () -> Unit,
    onRemove: () -> Unit,
    onCharUuidChange: (Int, String) -> Unit,
    onToggleProperty: (Int, CharProperty) -> Unit,
    enabled: Boolean,
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Service ${serviceIndex + 1}", style = MaterialTheme.typography.labelMedium)
                if (enabled) TextButton(onClick = onRemove) { Text("Remove") }
            }

            OutlinedTextField(
                value = service.uuid, onValueChange = onUuidChange,
                label = { Text("Service UUID") }, modifier = Modifier.fillMaxWidth(),
                singleLine = true, enabled = enabled,
            )

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()

            service.characteristics.forEachIndexed { ci, char ->
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = char.uuid, onValueChange = { onCharUuidChange(ci, it) },
                    label = { Text("Char ${ci + 1} UUID") }, modifier = Modifier.fillMaxWidth(),
                    singleLine = true, enabled = enabled,
                )
                FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    CharProperty.entries.forEach { prop ->
                        FilterChip(
                            selected = prop in char.properties, onClick = { onToggleProperty(ci, prop) },
                            label = { Text(prop.name) }, enabled = enabled,
                        )
                    }
                }
            }

            if (enabled) TextButton(onClick = onAddChar) { Text("+ Add Characteristic") }
        }
    }
}
