const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

let publicPath = "";
const port = process.env.VUE_APP_PORT || 3090;
if (port == "3090") {
  let publicPath = './';
} else {
  let publicPath = `http://baoding.dreamsky0822.asia:${port}/`;
}


module.exports = {
  devServer: {
    port: port, // 使用变量配置端口
    allowedHosts: 'all',
  },
  publicPath: publicPath, // 使用组合后的公共路径
};
