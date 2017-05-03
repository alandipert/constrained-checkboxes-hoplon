# constrained-checkboxes-hoplon

This is a little [Hoplon][3] demo to demonstrate how one might implement
"constrained checkboxes". These are a group of checkboxes, only some of which
may be selected by the user at one time. The best way to understand it is
to [look at it.](https://tailrecursion.com/~alan/constrained-checkboxes-hoplon/)

This project was inspired by this
[tweet by Evan Czaplicki](https://twitter.com/czaplic/status/859065124006445056)
that shows how to solve the problem in [Elm](http://elm-lang.org/).

## Implementation notes

The heart of the implementation is
the [linked set](https://github.com/frankiesardo/linked) data structure by
Frankie Sardo. This is a set that maintains elements in insertion order.

Retaining the n-most-recently-added elements in one of these linked sets is
really easy. You just calculate how much smaller the set should be, take that
many elements from the seq view of the set, and `disj` them. The `retain`
function in the code implements this.

The other kind of tricky part is handling user input nicely. There aren't native
"check" and "uncheck" events on `input` elements of type `"checkbox"`. Even
jQuery doesn't provide them.

Fortunately, in Hoplon, one can easily implement new kinds of event handlers by
extending the `hoplon.core/on!` multimethod. In the example, I create
implementations for `:check` and `:uncheck` events.

## Dependencies

- java 1.7+
- [boot][1]

## Usage
### Development
1. Start the `dev` task. In a terminal run:
    ```bash
    $ boot dev
    ```
    This will give you a  Hoplon development setup with:
    - auto compilation on file changes
    - audible warning for compilation success or failures
    - auto reload the html page on changes
    - Clojurescript REPL

2. Go to [http://localhost:8000][2] in your browser. You should see "Hello, Hoplon!".

3. If you edit and save a file, the task will recompile the code and reload the
   browser to show the updated version.

### Production
1. Run the `prod` task. In a terminal run:
    ```bash
    $ boot prod
    ```

2. The compiled files will be on the `target/` directory. This will use
   advanced compilation and prerender the html.

## License

Copyright © 2017, Alan Dipert

[1]: http://boot-clj.com
[2]: http://localhost:8000
[3]: http://hoplon.io
