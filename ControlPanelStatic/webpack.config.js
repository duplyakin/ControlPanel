var path = require('path');
var webpack = require('webpack');

module.exports = {
    entry: {
        main: './src/main/js/index.js'
    },
    plugins: [
        new webpack.DefinePlugin({
            SERVER_PATH: JSON.stringify("http://localhost:8090")
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
    devtool: 'inline-source-map',
    devServer: {
        contentBase: path.resolve(__dirname, "build"),
        port: 9090,
        publicPath: '/public/',
        historyApiFallback: true,
        proxy: {
            secure: true,
            target: "http://localhost:8090",
        }
    }
};