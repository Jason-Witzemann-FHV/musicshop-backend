import axios from "axios"
import { get } from 'svelte/store'
import { token } from '../storage/SessionStorage.js'
import { basket } from '../storage/BasketStorage.js'
import { Snackbar } from "svelma"

export function addToBasket(releaseId) {

    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.put(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket/add/${releaseId}`, {}, config)
        .then(response => {
            Snackbar.create({ 
                message: 'Item added to basket!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
        })
}

export function getBasket() {

    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.get(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket`, config)
        .then(response => basket.update(old => response.data) )

}

export function removeFromBasket(releaseId) {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.delete(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket/remove/${releaseId}`, config)
        .then(response => {
            Snackbar.create({ 
                message: 'Item removed from basket!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
            getBasket()
        })
}

export function changeQuantity(releaseId, newQuantity) {

    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.put(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket/add/${releaseId}/${newQuantity}`, {}, config)
}

export function clearBasket() {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.delete(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket/clear`, config)
        .then(response => {
            Snackbar.create({ 
                message: 'Basket cleared!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
            getBasket()
        })
}

export function sellBasket() {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.post(`http://localhost:8080/backend-1.0-SNAPSHOT/soundkraut/basket/selfsell`, {}, config)
        .then(response => {
            Snackbar.create({ 
                message: 'Item bought all items!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
            getBasket()
        }).catch(error => {
            Snackbar.create({ 
                message: 'Error: at least one item is out of stock!',
                type: "is-error",
                background: 'has-background-grey-lighter'
            })
        })
}