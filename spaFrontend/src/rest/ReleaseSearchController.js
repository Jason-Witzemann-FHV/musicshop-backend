import { get } from 'svelte/store';
import axios from "axios";

import { releaseSearchResult, detailedSearchResult, releaseSearchTitle, releaseSearchArtist, releaseSearchGenre } from '../storage/ReleaseSearchStore.js';
import { getMainBackend } from "./Adresses.js";

export function searchReleases() {

    let title = get(releaseSearchTitle)
    let artist = get(releaseSearchArtist)
    let genre = get(releaseSearchGenre)

    axios.get(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/search/query?title=${title}&artist=${artist}&genre=${genre}`)
        .then(response => releaseSearchResult.update(old => response.data))
}

export function searchDetails(releaseId) {
    axios.get(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/search/id/${releaseId}`)
        .then(response => detailedSearchResult.update(old => response.data))
}