package com.mateuszholik.uicomponents.dialog

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.window.Dialog
import com.mateuszholik.designsystem.cornerRadius
import com.mateuszholik.designsystem.spacing
import com.mateuszholik.uicomponents.text.TitleMediumText
import com.mateuszholik.uicomponents.textfield.CommonOutlinedTextField

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> CommonSearchDialog(
    items: List<T>,
    title: String,
    searchHint: String,
    onDismissRequest: () -> Unit,
    predicate: (T, String) -> Boolean,
    onEmptySearchContent: @Composable LazyItemScope.() -> Unit,
    itemsContent: @Composable LazyItemScope.(T) -> Unit,
) {
    var filteredElements by remember { mutableStateOf(items) }
    var searchedText by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(shape = RoundedCornerShape(MaterialTheme.cornerRadius.normal)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.normal),
                contentPadding = PaddingValues(MaterialTheme.spacing.normal)
            ) {
                item { TitleMediumText(text = title) }

                stickyHeader {
                    Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.surface)) {
                        CommonOutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            text = searchedText,
                            onTextChanged = { newText ->
                                searchedText = newText
                                filteredElements = if (newText.isEmpty()) {
                                    items
                                } else {
                                    items.filter { predicate(it, newText) }
                                }
                            },
                            hint = searchHint,
                            focusRequester = focusRequester
                        )
                    }
                }

                if (filteredElements.isEmpty()) {
                    item(content = onEmptySearchContent)
                } else {
                    items(
                        items = filteredElements,
                        itemContent = itemsContent
                    )
                }
            }
        }
    }
}
