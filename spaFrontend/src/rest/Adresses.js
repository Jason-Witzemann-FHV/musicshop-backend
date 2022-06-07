

let isDev = false

let devAdress = "localhost"
let mainBackendAddress = "10.0.40.160"
let playlistAddress = "35.159.33.140"
let downloadAdress = "3.72.41.194"

export function getMainBackend() {
    return isDev ? devAdress : mainBackendAddress
}

export function getPlaylist() {
    return isDev ? devAdress : playlistAddress
}

export function getDownload() {
    return isDev ? devAdress : downloadAdress
}