<script>
    import { playlist } from "../storage/PlaylistStorage.js"
    import { loadPlaylist } from "../rest/PlaylistController.js"
    import { onMount } from 'svelte';
	import { fly } from 'svelte/transition';
    import { faCloudArrowDown }  from "@fortawesome/free-solid-svg-icons"
    import { Input, Button } from "svelma"
    import Fa from "svelte-fa";

    onMount(async () => {
		loadPlaylist()
	});

	$: pageCount = parseInt($playlist.length / maxPageSize) + ($playlist.length % maxPageSize !== 0 ? 1 : 0)
	let navigationPage = 1
	let maxPageSize = 12

</script>

<h1 class="title">Your Playlist</h1>
<p class="subtitle">
	Take a look at all digital mediums you have ever bought!<br>
	Also, if you would like to download them, you can do that one at a time.
</p>

{#if $playlist.length !== 0}
    <div transition:fly="{{ y: -50, duration: 1000 }}">

        <table class="table is-fullwidth has-background-link-light">
            <thead>
                <th></th>
                <th>Title</th>
                <th>Artist</th>
                <th>Duration</th>
                <th></th>
            </thead>

            {#each $playlist as { id, title, artist, duration }, i }
                {#if (i - (navigationPage -1) * maxPageSize < maxPageSize) && i >= (navigationPage -1) * maxPageSize } 
                    <tr>
                        <td> {i + 1} </td>
                        <td> {title} </td>
                        <td> {artist} </td>
                        <td> {duration} </td>
                        <td><a><Fa icon={faCloudArrowDown} size="1.75x" /></a>
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
            </nav>
        {/if}

        
    </div>
{:else}
    <p>Looks you do not own any songs yet... Would you like to buy some?</p>
{/if}