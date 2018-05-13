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
                        presets: ['env', 'react']
                    }
                }
            }
        ],
    },
    devServer: {
        contentBase: path.resolve(__dirname, "build"),
        inline: true,
        port: 9090
    },
};