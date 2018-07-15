var path = require('path');
var webpack = require('webpack');
var ExtractTextPlugin = require('extract-text-webpack-plugin')

module.exports = {
    entry: {
        main: './src/main/js/index.js'
    },
    plugins: [
        // Данный параметр описывает путь к динамике приложения.
        // Используется в mainActions#executeRequest
        // Переопределите, если java-часть приложения располагается по другому адресу.
        new webpack.DefinePlugin({
            SERVER_PATH: JSON.stringify("http://localhost:8090")
        }),
        // для использования 'extract-text-webpack-plugin'
        // https://github.com/webpack-contrib/extract-text-webpack-plugin
        new ExtractTextPlugin("styles.css"),
    ],

    output: {
        path: path.resolve(__dirname, "build/public"),
        filename: 'main.js',
    },
    module: {
        rules: [
            {
                test: /\.js$|jsx$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: ['env', 'react'],
                        plugins: ["babel-plugin-transform-class-properties"]
                    }
                }
            },
            {
                test: /\.(png|woff|woff2|eot|ttf|svg)$/,
                loader: "url?limit=5000"
            },
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    fallback: 'style-loader',
                    use: 'css-loader'
                })
            }
        ]
    },
    //https://webpack.js.org/configuration/devtool/
    // Позволяет понять, где случилась беда
    devtool: 'hidden-source-map',
    devServer: {
        contentBase: path.resolve(__dirname, "build"),
        port: 9090,
        publicPath: '/public/',
        historyApiFallback: true,
        proxy: {
            secure: true,
            // Данный параметр описывает путь к динамике приложения и позволяет переадресовывать туда запросы.
            // Переопределите, если dev-сервер взаимодействует с java-приложением расположенным по другому адресу.
            target: "http://localhost:8090",
        }
    }
};