package com.rprihodko.habrareader.common.interfaces

import com.rprihodko.habrareader.common.dto.PostPreview

interface OnPostListener {
    fun onPostClick(post: PostPreview)
}