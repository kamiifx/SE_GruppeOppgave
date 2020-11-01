<template id="app">
    <div class="wrapper">
        <header>
            <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
            <h3>Parkering<br> App</h3>
            <div class="logins">
                <button class="button-user-login" v-on:click="modalActive = true">Logout</button>
            </div>
        </header>

        <main>
            <h2>Main APP Page</h2>

        </main>

    </div>
</template>
<script>
    Vue.component("app", {
        template: "#app",
        methods:({
            openModal: function () {
            }
        }),
        data:() =>({
            users:[]
        }),
        created() {
            fetch("/api/users")
                .then(res => res.json())
                .then(res => {
                    this.users = res;
                }).catch(() => alert("Error getting user"));
        }
    });
</script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Roboto:wght@100;300;400;500;700;900&display=swap');
    header img{
        width: 40px;
        margin: 30px;
    }
    header{
        display: flex;
        flex-direction: row;
        align-items: center;
        width: 100%;
        position: absolute;
        top: 0;
        left: 0;
        z-index: 9999;
        -webkit-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
        -moz-box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
        box-shadow: 0px 10px 36px -15px rgba(0,0,0,0.50);
    }
    header h3{
        font-family: 'Roboto', sans-serif;
        font-weight: 300;
    }
    header button{
        font-family: 'Roboto', sans-serif;
        font-size: 17px;
        font-weight: 400;
        color: #222f3e;
        height: 30px;
        border-radius: 5px;
        border-style: none;
    }
    header button:hover{
        cursor: pointer;
        transition: 0.5s ease-in-out;
        background-color: #b8e994;
    }
    .logins{
        margin-left: 84%;
    }
    .button-user-login{
        margin-left: 15px;
        background-color: #1dd1a1;
    }

</style>
