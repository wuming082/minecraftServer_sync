import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import router from './router';
import VueClipBoard from 'vue-clipboard2' //剪贴板库
 
//import axios from 'axios'

Vue.config.productionTip = false;
Vue.use(ElementUI);
Vue.use(VueClipBoard); 

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
