const { BlockBlobClient } = require("@azure/storage-blob");

var mainUrl = "api/images";

export function upload() {
    var name = document.getElementById("name").value;
    var version = document.getElementById("version").value;
    var dataToSend = {};
    dataToSend.name = document.getElementById("name").value;

    if (version === "1.1") {
        dataToSend.description = document.getElementById("description").value;
    }

    if (!name || name.length < 3) {
        showError("Enter at least three characters for the title.");
        return;
    }

    var file = document.getElementById('fileInput').files[0];
    if (!file) {
        showError("Choose a file.");
        return;
    }

        fetch(mainUrl + "?api-version=" + version, {
            method: "POST",
            headers: {
                "Content-Type": "application/json" // mime-types
            },
            body: JSON.stringify(dataToSend)
        }).then(response => {
            return response.json();
        }).then(responseJson => {
            startUploadSuccess(responseJson);
        });
}
window.upload = upload;

export function showError(error) {
    document.getElementById("error").style.display = "block";
    document.getElementById("error").innerHTML = error;
    setTimeout(function () {
        document.getElementById("error").style.display = "none";
    }, 3000);
}

export async function startUploadSuccess(data) {
    var file = document.getElementById('fileInput').files[0];

    const blockBlobClient = new BlockBlobClient(data.uploadUrl);

    await blockBlobClient.uploadBrowserData(file);

    uploadComplete(data.id);
}

export function uploadComplete(id) {
    var version = document.getElementById("version").value;
    fetch(mainUrl + "/" + id + "/uploadComplete" + "?api-version=" + version, {
        method: "POST",
    }).then(response => {
        return response.json();
    }).then(responseJson => {
        refreshImages();
    });
}

export function refreshImages() {
    var version = document.getElementById("version").value;
    fetch(mainUrl + "?api-version=" + version)
        .then(response => response.json())
        .then(responseJson => {
            refreshImagesResult(responseJson);
        });
}
window.refreshImages = refreshImages;

export function refreshImagesResult(data) {
    var imagesDiv = document.getElementById("images");
    imagesDiv.innerHTML = '';

    // data is an array of ImageEntity
    data.forEach(addImage);
}

export function purge() {
    var version = document.getElementById("version").value;
    fetch(mainUrl + "?api-version=" + version, {
        method: "Delete"
    }).then(() => {
        document.getElementById("images").innerHTML = "";
    });
}
window.purge = purge;

export function purgeResult() {
    document.getElementById("images").innerHTML = "";
}

export function addImage(image) {
    var version = document.getElementById("version").value;
    var imagesDiv = document.getElementById("images");

    var img = document.createElement("img");
    img.classList = "size0"; //
    img.src = mainUrl + "/" + image.id + "?api-version=" + version;

    if (version = "1.1" && image.description != null && image.description != "") {
        img.title = image.description;
        img.alt = image.description;
    } else {
        img.title = image.name;
        img.alt = image.name;
    }

    imagesDiv.appendChild(img); 
}
window.onload = refreshImages;