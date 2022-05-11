<script>
	import SearchReleases from "./components/SearchReleases.svelte";
	import { Field, Icon, Button } from "svelma";

	import Fa from "svelte-fa";
	import {
		faRecordVinyl,
		faMagnifyingGlass,
		faMusic,
		faShoppingBasket,
	} from "@fortawesome/free-solid-svg-icons";

	import "bulma/css/bulma.css";

	let currentPage = "searchReleases"; // valid: "login", "searchSongs", "basket", "playlist", "searchReleases"
</script>

<nav class="navbar is-link is-normal">
	<div class="navbar-brand">
		<div class="navbar-item">
			<h2><b>MicroSounds</b></h2>
		</div>
	</div>

	<div id="navbarBasicExample" class="navbar-menu">
		<div class="navbar-start">
			<a class="navbar-item ml-6" on:click={() => currentPage = "searchReleases"}>
				<Fa icon={faRecordVinyl} /> &nbsp; Search Releases
			</a>

			<a class="navbar-item ml-2" on:click={() => currentPage = "searchSongs"}>
				<Fa icon={faMagnifyingGlass} /> &nbsp; Search Songs
			</a>

			<a class="navbar-item ml-2" on:click={() => currentPage = "basket"}>
				<Fa icon={faShoppingBasket} /> &nbsp; Basket
			</a>

			<a class="navbar-item ml-2" on:click={() => currentPage = "playlist"}>
				<Fa icon={faMusic} /> &nbsp; My Playlist
			</a>
		</div>

		<div class="navbar-end">
			<div class="navbar-item">
				<Button type="is-info" on:click={() => currentPage = "login"}>Login</Button>
			</div>
		</div>
	</div>
</nav>

<div class="columns mt-6">
	<div class="column is-2" />
	<div class="column is-8">
		{#if currentPage === "searchReleases"}
			<SearchReleases />
		{:else if currentPage === "login"}
			<!-- todo login page -->
		{:else if currentPage === "searchSongs"}
			<!-- todo search song page -->
		{:else if currentPage === "basket"}
			<!-- todo basket page -->
		{:else if currentPage === "playlist"}
			<!-- todo playlist page -->
		{/if}
	</div>
</div>
