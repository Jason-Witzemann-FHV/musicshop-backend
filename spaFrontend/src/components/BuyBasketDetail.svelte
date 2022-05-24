<script>
    import { showBuyDetails, basket } from "../storage/BasketStorage.js";
    import { number, type, cvc } from "../storage/CreditCardStorage.js";
    import { sellBasket } from "../rest/BasketController.js"
    import { Field, Input, Modal } from "svelma";
    import { toPrice} from "../Utils"

    $: totalPrice = toPrice($basket.map(item => item.price * item.quantity).reduce((a, b) => a + b) * 1.2)

</script>

{#if $showBuyDetails }
    <Modal bind:$showBuyDetails>
        <header class="modal-card-head has-background-link-light">
            <p class="modal-card-title">Buy content of your basket</p>
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
                <div class="column">
                    <p>Total price: {totalPrice}</p>
                </div>
                <div class="column has-text-right is-right">
                    <button class="button is-link is-outlined" on:click={() => $showBuyDetails = false}>Cancel</button>
                    <button class="button is-link" on:click={() => {sellBasket() ; showBuyDetails.update(old => false)}}>Buy</button>
                </div>
            </div>
        </section>

        <footer class="modal-card-foot has-background-link-light">

        </footer>
    </Modal>
{/if}
