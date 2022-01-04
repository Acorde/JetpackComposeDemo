package com.igor.jetpackcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import com.igor.jetpackcompose.ui.theme.JetpackComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeTheme {
                BuildActivitiesList()
            }
        }
    }

    @Composable
    private fun BuildActivitiesList() {
        LazyColumn {
            items(ActivitiesEnum.values()) {
                ActivityItemView(it)
            }
        }
    }

    @Composable
    private fun ActivityItemView(activityEnum: ActivitiesEnum) {

        Row(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Surface(shape = MaterialTheme.shapes.medium, elevation = 7.dp,
                modifier = Modifier.clickable {
                    navigateToActivity(activityEnum)
                }) {
                Text(
                    text = activityEnum.name,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle2
                )
            }
        }
    }

    private fun navigateToActivity(activityEnum: ActivitiesEnum) {
        val intent = when (activityEnum) {
            ActivitiesEnum.MESSAGE_LIST_ACTIVITY -> Intent(this, MessagesListActivity::class.java)
            ActivitiesEnum.ONE_MESSAGE_ACTIVITY -> Intent(this, OneMessageActivity::class.java)
            ActivitiesEnum.COLUMN_EXAMPLE_ACTIVITY -> Intent(
                this,
                ColumnActivity::class.java
            )
            ActivitiesEnum.ROW_EXAMPLE_ACTIVITY -> Intent(this, RowActivity::class.java)
            ActivitiesEnum.BOX_EXAMPLE_ACTIVITY -> Intent(this, BoxLayoutActivity::class.java)
        }

        startActivity(intent)

    }

    enum class ActivitiesEnum {
        ONE_MESSAGE_ACTIVITY,
        MESSAGE_LIST_ACTIVITY,
        COLUMN_EXAMPLE_ACTIVITY,
        ROW_EXAMPLE_ACTIVITY,
        BOX_EXAMPLE_ACTIVITY
    }
}


