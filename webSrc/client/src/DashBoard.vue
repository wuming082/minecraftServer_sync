<template>
    <div id="DashBoard">
        <headDisplayVue></headDisplayVue>
        <leftDisplayVue @addcontainer="addcontainer" @dismisscontainer="dismisscontainer"></leftDisplayVue>
        <mainDisplayVue :containerID="containerIDdismiss" :containerCount="containerCount"></mainDisplayVue>
    </div>
</template>

<script>

import headDisplayVue from './components/headDisplay.vue'
import leftDisplayVue from './components/leftDisplay.vue'
import mainDisplayVue from './components/mainDisplay.vue'


export default {
    name: 'DashBoard',
    components: {
        headDisplayVue,
        leftDisplayVue,
        mainDisplayVue,
    },
    data() {
        return {
            containerIDdismiss: null,
            containerCount: 0,
        }
    },
    methods: {
        addcontainer() {
            if (this.containerCount >= 0) {
                this.containerCount++;
            }
        },
        dismisscontainer() {
            if (this.containerCount > 0) {
                this.containerCount--;
            }
        }

    }
}
</script>

<style>
#DashBoard {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
}
</style>
