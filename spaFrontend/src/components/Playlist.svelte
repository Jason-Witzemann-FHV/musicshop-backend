<script>
    import { playlist } from "../storage/PlaylistStorage.js"
    import {loadPlaylist, downloadSong, getSongResource} from "../rest/PlaylistController.js"
    import { onMount } from 'svelte';
    import { faCloudArrowDown }  from "@fortawesome/free-solid-svg-icons"
    import { toDuration} from "../Utils"
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
    <div id:fly="{{ y: -50, duration: 1000 }}">

        <table class="table is-fullwidth has-background-link-light">
            <thead>
                <th></th>
                <th>Title</th>
                <th>Artist</th>
                <th>Duration</th>
                <th></th>
                <th></th>
            </thead>

            {#each $playlist as { songId, title, artist, duration }, i }
                {#if (i - (navigationPage -1) * maxPageSize < maxPageSize) && i >= (navigationPage -1) * maxPageSize } 
                    <tr>
                        <td> {i + 1} </td>
                        <td> {title} </td>
                        <td> {artist} </td>
                        <td> {toDuration(duration)} </td>
                        <!-- svelte-ignore a11y-missing-attribute -->
                        <td><a on:click={downloadSong(songId, title)}><Fa icon={faCloudArrowDown} size="1.75x" /></a>
                        </td>
                        <td>
                            <audio controls>
                                {#await getSongResource(songId)}
                                    loading...
                                {:then url}
                                    <source src="{url}">
                                {/await}
                            </audio>
                        </td>
                    </tr>
                {/if}
            {/each}

        </table>

        {#if pageCount > 1} 
            <nav class="pagination is-centered">

                <ul class="pagination-list">
                    {#if navigationPage !== 1}
                        <!-- svelte-ignore a11y-missing-attribute -->
                        <li><a class="pagination-link" on:click={() => navigationPage = 1}>1</a></li>

                        <li><span class="pagination-ellipsis">&hellip;</span></li>
                        <!-- svelte-ignore a11y-missing-attribute -->
                        <li><a class="pagination-link" on:click={() => navigationPage--}>{navigationPage - 1}</a></li>
                    {/if}
                    <!-- svelte-ignore a11y-missing-attribute -->
                    <li><a class="pagination-link is-current">{navigationPage}</a></li>

                    {#if navigationPage !== pageCount}
                        <!-- svelte-ignore a11y-missing-attribute -->
                        <li><a class="pagination-link" on:click={() => navigationPage++}>{navigationPage + 1}</a></li>

                        <li><span class="pagination-ellipsis">&hellip;</span></li>
                        <!-- svelte-ignore a11y-missing-attribute -->
                        <li><a class="pagination-link" on:click={() => navigationPage = pageCount}>{pageCount}</a></li>
                    {/if}
                </ul>
            </nav>
        {/if}

        
    </div>
{:else}
    <p>Looks you do not own any songs yet... Would you like to buy some?</p>
{/if}