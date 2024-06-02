document.getElementById("filter-menu__filter-by").addEventListener("change", function () {
    var sortBy = this.value;
    var tagsDropdown = document.getElementById("filter-menu__tags-dropdown");

    // Hide or show the tags dropdown based on the selected option
    if (sortBy === "tags") {
        tagsDropdown.style.display = "block";
    } else {
        tagsDropdown.style.display = "none";
    }
});