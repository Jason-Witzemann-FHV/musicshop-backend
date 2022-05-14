import { writable } from 'svelte/store'

export const currentView = writable("searchReleases") // valid: "login", "basket", "playlist", "searchReleases"