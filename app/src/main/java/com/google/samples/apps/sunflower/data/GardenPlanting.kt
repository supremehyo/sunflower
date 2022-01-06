/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Calendar

/**
 * [GardenPlanting] represents when a user adds a [Plant] to their garden, with useful metadata.
 * Properties such as [lastWateringDate] are used for notifications (such as when to water the
 * plant).
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(
    tableName = "garden_plantings",
    //여기서는 외래키 설정이 있다.
    //Plant라는 Entity와 관계를 맺을거고 parentColums는 상위 항목의
    //기본 키 열 이름으로 설정(Plant Entity의 id)하고 childColumns에는
    //현재의 Entity인 GardenPlanting의 plant_id 키를 설정한다.
    //이때 parentColums와 childColumns 개수는 동일해야한다.
    foreignKeys = [
        ForeignKey(entity = Plant::class, parentColumns = ["id"], childColumns = ["plant_id"])
    ],
    indices = [Index("plant_id")]//indicies 는인덱싱을 해서
    // SELECT 쿼리의 속도를 높여주는 역할을 한다.
    // plant_id 로 인덱싱을 설정하였다.
)
data class GardenPlanting(
    @ColumnInfo(name = "plant_id") val plantId: String,

    /**
     * Indicates when the [Plant] was planted. Used for showing notification when it's time
     * to harvest the plant.
     */
    @ColumnInfo(name = "plant_date") val plantDate: Calendar = Calendar.getInstance(),

    /**
     * Indicates when the [Plant] was last watered. Used for showing notification when it's
     * time to water the plant.
     */
    @ColumnInfo(name = "last_watering_date")
    val lastWateringDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var gardenPlantingId: Long = 0
}
