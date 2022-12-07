class Directory(
    val name: String,
    val files: MutableSet<File> = mutableSetOf(),
    val directories: MutableSet<Directory> = mutableSetOf(),
    val parent: Directory?
) {

    fun addFile(file: File) {
        files.add(file);
    }

    fun addDirectory(directory: Directory) {
        directories.add(directory)
    }

    fun getAllDirectories(): List<Directory> {
        val directoryList = directories.map { it.getAllDirectories() }.flatten().toMutableList()
        directoryList.add(this)
        return directoryList
    }

    fun getSize(): Int = files.map { it.size }.sum() + directories.map { it.getSize() }.sum()
}