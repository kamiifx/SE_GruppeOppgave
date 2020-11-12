<template id="parking-spaces-overview">
  <div class="grid">
    <header>
      <img src="https://www.flaticon.com/svg/static/icons/svg/1159/1159499.svg" alt="logo">
      <h3>Parkering<br> App</h3>
      <div class="logins">
        <button class="button-admin-login">Admin Login</button>
        <button class="button-user-login" v-on:click="modalActive = true">Login</button>
      </div>
    </header>
    <main>
      <h1>Ledige parkeringsplasser</h1>
      <ul class="parking-spaces-overview-list">
        <li v-for="parkingspace in parkingspaces">
          <a :href=`/app/${userId}/parkingspaces/${parkingspace.spaceId}`>
            <div class="single-parking-space-container">
              <img src="https://seeklogo.com/images/T/traffic-signs-logo-7823141A70-seeklogo.com.png" alt="Bilde av parkeringslogo" />
              <h3>By: {{parkingspace.city}}</h3>
              <h3>Adresse: {{parkingspace.address}}</h3>
              <h3>Størrelse: {{parkingspace.size_sqm}}</h3>
              <h3>Pris per time: {{parkingspace.price_ph}}</h3>
            </div>
          </a>
        </li>
      </ul>
    </main>
  </div>
</template>
<script>
Vue.component("parking-spaces-overview", {
  template: "#parking-spaces-overview",
  data: () => ({
    parkingspaces: [],
    userId: null,
  }),
  created() {
    this.userId = this.$javalin.pathParams["userId"];
    fetch("/api/parking-spaces")
        .then(res => res.json())
        .then(res => {
          this.parkingspaces = res;
        })
        .catch(() => alert("Error while fetching parkingspaces"));
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
.button-admin-login{
  background-color: #54a0ff;
}

li{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  justify-content: space-evenly;
}

div{
  box-sizing: border-box;
}

main{
  position: static;
  padding-top:50px;
  padding-left: 65px;
  margin: 0;
  height: 100vh;
  max-width: 100vw;
  max-height: 100vh;
  background: transparent;
  z-index: 0;
}

main ul{
  -moz-column-width: 18em;
  -webkit-column-width: 18em;
  column-width: 18em;
  -moz-column-gap: 1em;
  -webkit-column-gap: 1em;
  column-gap: 1em;
  margin: 5px;
}

div.single-parking-space-container{
  overflow: hidden;
  display: inline-block;
  width: 100%;
  height:auto;
  opacity: 0.96;
  margin: 10px;
  text-align: center;
  text-decoration: none;
  background-color: transparent;
  color: #212121;
  transition-duration: 0.3s;
}

div.single-parking-space-container img{
  width:30%;
  max-width: 50%;
  height: auto;
  box-sizing: border-box;
  border-radius: 5px;
}


div.single-parking-space-container:hover{
  background-color: rgba(86, 70, 70, 0.3);
}

</style>