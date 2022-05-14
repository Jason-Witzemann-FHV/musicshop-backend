<script>

    import { Field, Input, Button } from "svelma";

    import { userName, password } from "../storage/SessionStorage.js"

    import {login } from "../rest/SessionController.js"

</script>


<div class="columns mt-6">

    <div class="column is-4" />
    <div class="column is-4">

        <form class="box">

            <div class="content">
                <h3 class="has-text-centered">Login to Microsound</h3>
                <Field label="Username" class="mt-5">
                    <Input type="text" bind:value={$userName} />
                </Field>
                <Field label="Password" class="mt-4">
                    <Input type="password" bind:value={$password} />
                </Field>

                <p class="mt-5">
                    Forgot Password? <br>
                    <i class="has-text-grey-light">Well, that's bad, we can't help you with that.</i>
                </p>

                <div class="columns">
                    <div class="column is-7"/>
                    <div class="column is-5">
                        <Button type="is-info" class="is-fullwidth" on:click={() => login() }>Login</Button>
                    </div>
                </div>

            </div>


        </form>
    </div>

</div>