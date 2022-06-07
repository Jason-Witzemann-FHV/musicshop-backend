<script>
	import SearchReleases from "./components/SearchReleases.svelte"
	import Login from "./components/Login.svelte"
	import Basket from "./components/Basket.svelte"
	import Playlist from "./components/Playlist.svelte"
	import { Snackbar, Button } from "svelma"
	import { currentView } from "./storage/DisplayStorage.js"
    import { userName, password, token } from "./storage/SessionStorage.js"
	import { number, type, cvc } from "./storage/CreditCardStorage.js";

	import Fa from "svelte-fa";
	import {
		faRecordVinyl,
		faMagnifyingGlass,
		faMusic,
		faShoppingBasket,
		faUser,
		faCode
	} from "@fortawesome/free-solid-svg-icons";

	import "bulma/css/bulma.css";

	function logout() {
		$userName = "tf-test"
		$password = "PssWrd"
		$token = ""
		$currentView = "searchReleases"
		Snackbar.create({ 
            message: 'logged out successfully!',
            type: "is-link",
            background: 'has-background-grey-lighter'
        })
		$number = ""
		$type = ""
		$cvc = ""
	}

</script>

<nav class="navbar is-link is-normal">
	<div class="navbar-brand">
		<div class="navbar-item">
			<h2><b>MicroSounds</b></h2>
		</div>
	</div>

	<div id="navbarBasicExample" class="navbar-menu">
		<div class="navbar-start">
			<!-- svelte-ignore a11y-missing-attribute -->
			<a class="navbar-item ml-6" on:click={() => $currentView = "searchReleases"}>
				<Fa icon={faMagnifyingGlass} /> &nbsp; Search Releases
			</a>

			{#if $token !== ""}
			<!-- svelte-ignore a11y-missing-attribute -->
				<a class="navbar-item ml-2" on:click={() => $currentView = "basket"}>
					<Fa icon={faShoppingBasket} /> &nbsp; Basket
				</a>

				<!-- svelte-ignore a11y-missing-attribute -->
				<a class="navbar-item ml-2" on:click={() => $currentView = "playlist"}>
					<Fa icon={faMusic} /> &nbsp; My Playlist
				</a>
			{/if}

			<a class="navbar-item ml-2" href="https://app.swaggerhub.com/apis/Soundkraut/backend-1_0_snapshot_war/1.0">
				<Fa icon={faCode} /> &nbsp; Developer API
			</a>

		</div>

		<div class="navbar-end">
			<div class="navbar-item">
				{#if $token === ""}
					<Button type="is-info" on:click={() => $currentView = "login"}>Login</Button>
				{:else}
					<Fa icon={faUser} /> &nbsp; {$userName} 
					<Button type="is-info" class="ml-4" on:click={() => logout()}>Logout</Button>
				{/if}
				
			</div>
		</div>
	</div>
</nav>


<div class="columns mt-6">
	<div class="column is-2" />
	<div class="column is-8">
		{#if $currentView === "searchReleases"}
			<SearchReleases />
		{:else if $currentView === "login"}
			<Login />
		{:else if $currentView === "basket"}
			<Basket />
		{:else if $currentView === "playlist"}
			<Playlist />
		{/if}
	</div>
</div>
