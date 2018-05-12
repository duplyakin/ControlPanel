var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: {
        main: './src/main/js/index.js'
    },
    plugins: [
        new webpack.DefinePlugin({
            'SERVER_PATH': JSON.stringify("localhost:8090")
        })
    ],
    output: {
        filename: 'main.js',
        path: __dirname + "/src/main/resources/public",
    },
    module: {
        rules: [
            {
                test: /\.js$|jsx$/,
                exclude: /node_modules/,
                use: {
                    loader: "babel-loader",
                    options: {
                        presets: ['env', 'react']
                    }
                }
            }
        ],
    },
    devServer: {
        contentBase: __dirname + "/src/main/resources/templates/",
        inline: true,
        port: 9090
    },
};