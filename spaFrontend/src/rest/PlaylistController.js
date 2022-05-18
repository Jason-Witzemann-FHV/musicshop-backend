
import { playlist } from '../storage/PlaylistStorage.js';

export function loadPlaylist() {


    // todo rest calls

    let tempData = [
        {
            id: "adsf",
            title: "Title 1",
            artist: "Artist 1",
            duration: 140
        },
        {
            id: "fdsa",
            title: "Title 2",
            artist: "Artist 2",
            duration: 222
        },
        {
            id: "asde",
            title: "Title 3",
            artist: "Artist 3",
            duration: 200
        },
        {
            id: "hes",
            title: "Title 4",
            artist: "Artist 4",
            duration: 123
        },
    ]

    playlist.update(old => tempData)
}