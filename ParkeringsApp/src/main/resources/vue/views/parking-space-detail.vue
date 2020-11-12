<template id="parking-space-detail" @Rental-submitted="createRentalAgreement">
  <div class="content-wrapper">
    <div v-if="parkingSpace" class="detail-parking-space-container">
      <p>By: {{parkingSpace.city}}</p>
      <p>Addresse: {{parkingSpace.address}}</p>
      <p>Størrelse: {{parkingSpace.size_sqm}} m<sup>2</sup></p>
      <p>Pris per time: {{parkingSpace.price_ph}}</p>
    </div>
    <div class="form-style">
      <h2>Lei denne parkeringsplassen, {{user.name}}</h2>
      <form class="create" @submit="checkForm" :action=`/api/app/${user.id}/parkingspaces/${parkingSpace.spaceId}/rentspace` method="post">
        <p>
          <label for="duration">Hvor mange timer vil du leie denne plassen?<label>
            <input type="number" name="duration" id="duration" v-model="duration">
        </p>

        <p>
          <input type="submit" value="Lei plass">
        </p>

      </form>
    </div>
  </div>
</template>
<script>
Vue.component("parking-space-detail", {
  template: "#parking-space-detail",
  data: () => ({
    parkingSpace: null,
    user: null,
  }),
  created() {
    const spaceId = this.$javalin.pathParams["spaceId"];
    const userId = this.$javalin.pathParams["userId"];
    fetch(`/api/parking-spaces/${spaceId}`)
        .then(res => res.json())
        .then(res => {
          this.parkingSpace = res
        })
        .catch(() => alert("Error while fetching parking space"));

    fetch(`/api/users/${userId}`)
        .then(res => res.json())
        .then(res => {
          this.user = res;
        }).catch(() => alert("Error getting user"));
  }
});
</script>
<style>
div.detail-parking-space-container{
  padding: 10px;
  overflow: hidden;
  width: 750px;
  margin: 0 auto;
  background-color: rgba(20, 20, 20, 0.98);
  opacity: 0.96;
  color: white;
  -webkit-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
  -moz-box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
  box-shadow: 10px 10px 5px 0px rgba(0,0,0,0.25);
}


</style>
