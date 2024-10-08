package com.example.lislearnapp.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lislearnapp.feature_note.domain.util.NoteOrder
import com.example.lislearnapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit
) {

    Column(
        modifier = modifier,
    ) {
        Row {
            DefaultRadioButton(
                text = "Title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = {onOrderChange(NoteOrder.Title(noteOrder.orderType))}
            )

            Spacer(modifier = Modifier.run { width(2.dp) })

            DefaultRadioButton(
                text = "Date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {onOrderChange(NoteOrder.Date(noteOrder.orderType))}
            )

            Spacer(modifier = Modifier.run { width(2.dp) })

            DefaultRadioButton(
                text = "Color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {onOrderChange(NoteOrder.Color(noteOrder.orderType))}
            )

            Spacer(modifier = Modifier.run { width(2.dp) })

            DefaultRadioButton(
                text = "Language",
                selected = noteOrder is NoteOrder.Language,
                onSelect = {onOrderChange(NoteOrder.Language(noteOrder.orderType))}
            )
        }

        Spacer(modifier= Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            DefaultRadioButton(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {onOrderChange(noteOrder.copy(orderType = OrderType.Ascending))}
            )

            Spacer(modifier = Modifier.run { width(8.dp) })

            DefaultRadioButton(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {onOrderChange(noteOrder.copy(OrderType.Descending))}
            )
        }
    }

}
