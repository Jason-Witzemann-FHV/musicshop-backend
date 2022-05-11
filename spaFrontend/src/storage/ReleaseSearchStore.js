import { writable } from 'svelte/store'

export const releaseSearchResult = writable([])

export const detailedSearchResult = writable({})

export const isDetailActive = writable(false)

export const releaseSearchArtist = writable("")

export const releaseSearchTitle = writable("")

export const releaseSearchGenre = writable("")