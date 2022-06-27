package nu.brandrisk.kioskmode.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AppEntity (
    @PrimaryKey val packageName: String,
    val title: String,
    val isEnabled: Boolean
)
