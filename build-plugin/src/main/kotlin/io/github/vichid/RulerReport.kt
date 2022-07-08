package io.github.vichid

data class RulerReport(
    var name: String,
    var version: String,
    var variant: String,
    var downloadSize: Int,
    var installSize: Int,
    var components: List<Components>
)

data class Components(
    var name: String,
    var type: String,
    var downloadSize: Int,
    var installSize: Int,
    var files: List<Files>
)

data class Files(
    var name: String,
    var type: String,
    var downloadSize: Int,
    var installSize: Int
)
