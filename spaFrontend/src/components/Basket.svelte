<script>
    import { basket } from "../storage/BasketStorage.js"
    import { getBasket, removeFromBasket, changeQuantity, sellBasket, clearBasket } from "../rest/BasketController.js"
    import { onMount } from 'svelte';
	import { fly } from 'svelte/transition';
    import { faTrashCan }  from "@fortawesome/free-solid-svg-icons"
    import { Input, Button } from "svelma"
    import Fa from "svelte-fa";

    onMount(async () => {
		getBasket()
	});

	$: pageCount = parseInt($basket.length / maxPageSize) + ($basket.length % maxPageSize !== 0 ? 1 : 0)
	let navigationPage = 1
	let maxPageSize = 12

</script>



<h1 class="title">Let's have a look in your basket!</h1>
<p class="subtitle">
	Manage your basket items here.<br>
	If you are satisfied with your orders, then you can buy your songs. Digital mediums will be available in your playlist and the physical will be sent to your home.
</p>

{#if $basket.length !== 0}
    <div transition:fly="{{ y: -50, duration: 1000 }}">

        <table class="table is-fullwidth has-background-link-light">
            <thead>
                <th></th>
                <th>Title</th>
                <th>Medium</th>
                <th>Quantity</th>
                <th class="has-text-right">Price</th>
                <th></th>
            </thead>

            {#each $basket as { releaseId, title, quantity, stock, medium, price }, i }
                {#if (i - (navigationPage -1) * maxPageSize < maxPageSize) && i >= (navigationPage -1) * maxPageSize } 
                    <tr>
                        <td> {i + 1} </td>
                        <td> {title} </td>
                        <td> {medium} </td>
                        <td class="is-narrow"> <Input type="number" class="is-small" bind:value={quantity} max={stock} min={1} on:change={() => changeQuantity(releaseId, quantity)}/> </td>
                        <td class="has-text-right"> {`${parseFloat(price).toFixed(2)} €`} </td>
                        <td class="has-text-centered"> 
                            <a on:click={() => removeFromBasket(releaseId)}>
                                <Fa icon={faTrashCan} size="1.75x" />
                            </a>
                        </td>
                    </tr>
                {/if}
            {/each}

        </table>

        {#if pageCount > 1} 
            <nav class="pagination is-centered">

                <ul class="pagination-list">
                    {#if navigationPage !== 1}
                        <li><a class="pagination-link" on:click={() => navigationPage = 1}>1</a></li>

                        <li><span class="pagination-ellipsis">&hellip;</span></li>

                        <li><a class="pagination-link" on:click={() => navigationPage--}>{navigationPage - 1}</a></li>
                    {/if}

                    <li><a class="pagination-link is-current">{navigationPage}</a></li>

                    {#if navigationPage !== pageCount}
                        <li><a class="pagination-link" on:click={() => navigationPage++}>{navigationPage + 1}</a></li>

                        <li><span class="pagination-ellipsis">&hellip;</span></li>

                        <li><a class="pagination-link" on:click={() => navigationPage = pageCount}>{pageCount}</a></li>
                    {/if}
                </ul>

                <button class="pagination-next button is-link is-outlined" on:click={() => clearBasket() }>Reset</button>
                <Button class="pagination-next" type="is-link" on:click={() =>sellBasket()}>Buy All</Button>
            </nav>
        {:else}
            <div class="has-text-right">
                <button class="button is-link is-outlined" on:click={() => clearBasket() }>Reset</button>
                <Button type="is-link" on:click={() =>sellBasket()}>Buy All</Button>
            </div>

        {/if}

        
    </div>
{:else}
    <p>Looks like your basket is empty.</p>
{/if}