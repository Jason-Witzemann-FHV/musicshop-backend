<script>
    import { isDetailActive, detailedSearchResult } from "../storage/ReleaseSearchStore.js";
    import { Field, Input, Modal } from "svelma";


    $: formattedPrice = `${parseFloat($detailedSearchResult.price).toFixed(2)} €`

</script>

{#if $isDetailActive &&  $detailedSearchResult.recordings != null}
    <Modal bind:$isDetailActive>
        <header class="modal-card-head has-background-link-light">
            <p class="modal-card-title">{$detailedSearchResult.title}</p>
            <button class="delete" aria-label="close" on:click={() => isDetailActive.update(old => false)} />
        </header>

        <section class="modal-card-body">

            <div class="columns">
                <div class="column is-4">
                    <Field label="Medium">
                        <Input type="text" class="is-disabled" disabled bind:value={$detailedSearchResult.medium} />
                    </Field>
                </div>
                <div class="column is-4">
                    <Field label="Price">
                        <Input type="text" class="is-disabled" disabled bind:value={formattedPrice} />
                    </Field>
                </div>
                <div class="column is-4">
                    <Field label="Currently available">
                        <Input type="text" class="is-disabled" disabled bind:value={$detailedSearchResult.stock} />
                    </Field>
                </div>	
            </div>

            <table class="table is-fullwidth">
                <thead>
                    <th></th>
                    <th>Title</th>
                    <th>Duration</th>
                    <th>Artist(s)</th>
                </thead>
    
                {#each $detailedSearchResult.recordings as { title, duration, artists }, i }
                    <tr>
                        <td> {i + 1} </td>
                        <td> {title} </td>
                        <td> {duration} </td>
                        <td> {artists.join(", ")} </td>
                    </tr>
                {/each}
            </table>

        </section>

        <footer class="modal-card-foot has-background-link-light">
        </footer>
    </Modal>
{/if}
