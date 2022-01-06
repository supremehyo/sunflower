/*
 * Copyright 2019 Google LLC
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

package com.google.samples.apps.sunflower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.google.samples.apps.sunflower.adapters.MY_GARDEN_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.PLANT_LIST_PAGE_INDEX
import com.google.samples.apps.sunflower.adapters.SunflowerPagerAdapter
import com.google.samples.apps.sunflower.databinding.FragmentViewPagerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 데이터바인딩 세팅
        // FragmentViewPagerBinding / XML은 <layout>으로 데이터바인딩 처리
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        //어뎁터 연결
        viewPager.adapter = SunflowerPagerAdapter(this)

        // 탭 레이아웃 아이콘 및 텍스트 세팅 (TabLayoutMediator 는 처음 봤다.)
        // https://developer.android.com/training/animation/vp2-migration?hl=ko#tablayout 와 연계된다.
        // Set the icon and text for each tab
        //TabLayoutMediator 은 viewpager2와 tablayout 을 동시에 통합해서 만들 수 있게 해준다.
        //그러나 xml 상에서 같은 동일한 수준에 선언 되어있어야한다.

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()
        // 툴바 세팅
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        // 데이터바인딩과 초기화 작업을 한후 데이터바인딩 클래스에는 상응하는 레이아웃 파일의
        // 루트 뷰에 관한 직접 참조를 제공하는 binding.root 를 반환해준다.
        return binding.root
    }
    // 아이콘 리소스 가져오기
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> R.drawable.garden_tab_selector
            PLANT_LIST_PAGE_INDEX -> R.drawable.plant_list_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }
    // 타이틀 텍스트 가져오기
    private fun getTabTitle(position: Int): String? {
        return when (position) {
            MY_GARDEN_PAGE_INDEX -> getString(R.string.my_garden_title)
            PLANT_LIST_PAGE_INDEX -> getString(R.string.plant_list_title)
            else -> null
        }
    }
}
