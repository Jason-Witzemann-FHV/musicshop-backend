
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