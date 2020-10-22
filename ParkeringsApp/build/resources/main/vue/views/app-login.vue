<template id="app-login">
    <div class="wrapper">
        <header>
            <h1>hei</h1>
        </header>
    </div>
</template>
<script>
    Vue.component("app-login", {
        template: "#app-login",
        data: () => ({
            //userData her,
        })/*,
        created() {
            fetch("/api/users")
                .then(res => res.json())
                .then(res => {
                    this.user = res;
                })
                .catch(() => alert("Error getting user"));
        }*/
    });
</script>