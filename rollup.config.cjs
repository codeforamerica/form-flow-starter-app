const commonJs = require('@rollup/plugin-commonjs');
const replace = require('@rollup/plugin-replace');
const resolve = require('@rollup/plugin-node-resolve');

module.exports = {
  input: [
    'src/main/resources/static/assets/js/index.js'
  ],
  output: {
    dir: 'generated/main/resources/static/assets/js',
    format: 'iife',
    sourcemap: true,
    entryFileNames: 'scripts.js'
  },
  plugins: [
    replace({
      'preventAssignment': true,
      'process.env.NODE_ENV': JSON.stringify(process.env.NODE_ENV)
    }),
    resolve(),
    commonJs()
  ]
};
