import axios from "axios"
import { number, type, cvc } from "../storage/CreditCardStorage.js";
import { get } from 'svelte/store'
import { token } from '../storage/SessionStorage.js'
import { basket, showBuyDetails } from '../storage/BasketStorage.js'
import { Snackbar } from "svelma"
import { getMainBackend } from "./Adresses.js";

export function addToBasket(releaseId) {

    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.put(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket/add/${releaseId}`, {}, config)
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

    axios.get(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket`, config)
        .then(response => basket.update(old => response.data) )

}

export function removeFromBasket(releaseId) {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.delete(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket/remove/${releaseId}`, config)
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

    axios.put(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket/add/${releaseId}/${newQuantity}`, {}, config)
}

export function clearBasket() {
    const config = {
        headers: { Authorization: `Bearer ${get(token)}` }
    }

    axios.delete(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket/clear`, config)
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
        headers: { 
            "Authorization": `Bearer ${get(token)}`,
            "Content-type": "application/json",
            "Accept": "*/*",
        }
    }

    const creditCard = JSON.stringify({
        number: get(number),
        type: get(type),
        cvc: get(cvc)
    })

    axios.post(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/basket/selfsell`, creditCard, config)
        .then(response => {
            Snackbar.create({ 
                message: 'Bought all items!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
            showBuyDetails.update(old => false)
            number.update(old => "")
            type.update(old => "")
            cvc.update(old => "")
            getBasket()
        }).catch(error => {

            let errorMessage;
            if (error.response.status === 401 ){
                errorMessage = "Error: You do not have permissions to buy Releases for yourself!"
            } else if (error.code === "ERR_BAD_REQUEST") {
                errorMessage = "Error: Invalid credit card information!"
            } else {
                errorMessage = "Error: at least one item is out of stock!'"
            }

            Snackbar.create({ 
                message: errorMessage,
                type: "is-error",
                background: 'has-background-grey-lighter'
            })
        })
}