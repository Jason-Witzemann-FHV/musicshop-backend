<script>
    import { showBuyDetails } from "../storage/BasketStorage.js";
    import { number, type, cvc } from "../storage/CreditCardStorage.js";
    import { sellBasket } from "../rest/BasketController.js"
    import { Field, Input, Modal, Button } from "svelma";


</script>

{#if $showBuyDetails }
    <Modal bind:$showBuyDetails>
        <header class="modal-card-head has-background-link-light">
            <p class="modal-card-title">Buy Basket Content</p>
            <button class="delete" aria-label="close" on:click={() => $showBuyDetails = false} />
        </header>

        <section class="modal-card-body">

            <div class="columns">
                <div class="column is-6">
                    <Field label="Credit Card Infos">
                        <Input type="text" bind:value={$number} placeholder="1234123412341234" />
                    </Field>
                </div>
                <div class="column is-3">
                    <Field label="Type">
                        <Input type="text" bind:value={$type} placeholder="VISA" />
                    </Field>
                </div>
                <div class="column is-3">
                    <Field label="CVC">
                        <Input type="text" bind:value={$cvc} placeholder="789"/>
                    </Field>
                </div>	
            </div>
            <div class="columns">
                <div class="column has-text-right is-right">
                    <button class="button is-link is-outlined" on:click={() => $showBuyDetails = false}>Cancel</button>
                    <button class="button is-link" on:click={() => sellBasket()}>Buy</button>
                </div>
            </div>
        </section>

        <footer class="modal-card-foot has-background-link-light">

        </footer>
    </Modal>
{/if}
