
import { playlist } from '../storage/PlaylistStorage.js';
import { token } from '../storage/SessionStorage.js'
import { get } from 'svelte/store';
import axios from "axios";

export function loadPlaylist() {

    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.get(`http://localhost:8083/playlist`, config)
        .then(response => playlist.update(old => response.data))
}

export function downloadSong(songId, name) {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` },
        responseType: 'blob'
    }

    axios.get(`http://localhost:8082/download/${songId}`, config)
        .then((response) => {
            const url = window.URL.createObjectURL(new Blob([response.data]));
            const link = document.createElement('a');
            link.href = url;
            link.setAttribute('download', `${name}.mp3`); 
            document.body.appendChild(link);
            link.click();
        });

}

export async function getSongResource(songId) {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` },
        responseType: 'blob'
    }

    return await axios.get(`http://localhost:8082/download/${songId}`, config)
        .then((response) => window.URL.createObjectURL(new Blob([response.data])));

}