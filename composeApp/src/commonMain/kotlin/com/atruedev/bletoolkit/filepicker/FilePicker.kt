package com.atruedev.bletoolkit.filepicker

import androidx.compose.runtime.Composable

@Composable
expect fun rememberFilePicker(onResult: (FilePickerResult?) -> Unit): () -> Unit
