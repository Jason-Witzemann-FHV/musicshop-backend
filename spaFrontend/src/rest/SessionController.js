import { get } from 'svelte/store';
import axios from "axios";
import { Snackbar } from "svelma"

import { userName, password, token } from '../storage/SessionStorage.js';
import { currentView } from '../storage/DisplayStorage.js';
import { getMainBackend } from "./Adresses.js";

export function login() {

    let config = {
        headers: {
            "Accept": "*/*",
            "Content-type": "application/json" 
        }
    } 

    let bodyContent = JSON.stringify({
        "username": get(userName),
        "password": get(password)
    })

    axios.post(`http://${getMainBackend()}:8080/backend-1.0-SNAPSHOT/soundkraut/authentication`, bodyContent, config)
        .then(response => { 
            token.update(old => response.data) 
            currentView.update(old => "searchReleases")
            Snackbar.create({ 
                message: 'logged in sucessfully!',
                type: "is-link",
                background: 'has-background-grey-lighter'
            })
        })
        .catch(error => {
            Snackbar.create({ 
                message: 'invalid credentials, please try again!',
                type: "is-danger",
                background: 'has-background-grey-lighter'
            })
        })
}
