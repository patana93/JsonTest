package com.patana93.jsontest

data class Post constructor(
    var postHeading: String?,
    var postUrl: String?,
    var postAuthor: String?,
    var postImagePath: String?,
    var detail: ArrayList<Detail>?
    )
    {
}