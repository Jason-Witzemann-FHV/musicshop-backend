import { writable } from 'svelte/store'

export const basket = writable([])

export const showBuyDetails = writable(false)
