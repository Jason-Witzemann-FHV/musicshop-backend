<script>

	import { releaseSearchResult, isDetailActive, releaseSearchTitle, releaseSearchArtist, releaseSearchGenre } from '../storage/ReleaseSearchStore.js';
	import { searchReleases, searchDetails} from '../rest/ReleaseSearchController.js'

	import { fly } from 'svelte/transition';
	import { Field, Input, Button, Select } from 'svelma'
	import SearchReleaseDetail from "./SearchReleaseDetail.svelte";

	import Fa from "svelte-fa";
	import { faInfoCircle } from "@fortawesome/free-solid-svg-icons";

    function resetSearch() {
		$releaseSearchResult = [];
	}

	$: pageCount = parseInt($releaseSearchResult.length / maxPageSize) + ($releaseSearchResult.length % maxPageSize !== 0 ? 1 : 0)
	let navigationPage = 1
	let maxPageSize = 10

	let genres = ["", "Pop", "Elektronik", "Schlager", "EDM", "Metal", "Rock", "Country", "Rap", "Hip Hop", "RNB", "Acoustic"]

</script>

<h1 class="title">Explore our local sortiment!</h1>
<p class="subtitle">
	MicroSounds is a part of the well known SoundKraut Music Distribution Organization! <br>
	SoundKraut mainly sells physical releases, which you can look up here.
</p>
<div class="columns">
	<div class="column is-4">
		<Field label="Title of album or song">
            <Input type="text" bind:value={$releaseSearchTitle} />
        </Field>
	</div>
	<div class="column is-4">
		<Field label="Artist">
            <Input type="text" bind:value={$releaseSearchArtist} />
        </Field>
	</div>
	<div class="column is-2">
		<Field label="Genre">

			<Select bind:selected={$releaseSearchGenre} expanded>
				{#each genres as genre}
					<option value={genre}>{genre}</option>
				{/each}
			</Select>
        </Field>
	</div>	
	<div class="column is-2">
		<Field label="&#8203 ">
            <Button type="is-info" on:click={ searchReleases }>Check!</Button>
			{#if $releaseSearchResult.length > 0 } 
				<Button type="is-disabled has-text-centered" on:click={resetSearch}>Reset</Button>
			{/if}
        </Field>
	</div>

</div>

{#if $releaseSearchResult.length > 0 } 
	<div transition:fly="{{ y: -50, duration: 1000 }}">

		<table class="table is-fullwidth has-background-link-light">
			<thead>
				<th></th>
				<th>Album</th>
				<th>Medium</th>
				<th>Stock</th>
				<th>Price</th>
				<th></th>
			</thead>

			{#each $releaseSearchResult as { id, title, medium, stock, price }, i }
				{#if (i - (navigationPage -1) * maxPageSize < maxPageSize) && i >= (navigationPage -1) * maxPageSize } 
					<tr>
						<td> {i + 1} </td>
						<td> {title} </td>
						<td> {medium} </td>
						<td> {stock} </td>
						<td> {`${parseFloat(price).toFixed(2)} €`} </td>
						<td> 
							<a on:click={() => {isDetailActive.update(active => !active) ; searchDetails(id)} } >
								<Fa icon={faInfoCircle} size="1.75x" />
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
			</nav>
		{/if}

		
	</div>

	<SearchReleaseDetail />

{/if}





