# sicp-in-clojure

[Clojure](https://clojure.org/) unit tests for exercises in 
[Structure and Interpretation of Computer Programs](https://mitpress.mit.edu/sites/default/files/sicp/index.html)
([SICP](https://mitpress.mit.edu/sites/default/files/sicp/index.html)).


## Usage

- `master` branch contains only unit tests and solution templates. Hope
  this will be a useful study resource for anyone who wants to do SICP exercises in
  Clojure. Contributions are welcome!

- My own exercise solutions are in [`my_solutions`](https://github.com/sonlamho/sicp-in-clojure/tree/my_solutions)
  branch. Feel free to take a look!

- Running tests:

  + Example running a specific test:  ```lein test :only sicp-in-clojure.ex3-03-test```

    to test your solution for exercise 3.03. Do note that we use underscores `_`
    in filenames but dashes `-` in namespaces.

  + Run all tests: ```lein test```


- There's a convenient script to start a new exercise in `new_exercise.py`.
  Example usage:

    ```python3 new_exercise.py 1.23```

  This will create a solution file and a test file at the right locations with
  the appropriate namespaces, it will skip if a file already exists.

## Contributing guide

- Do use ```new_exercise.py``` convenient script to add an exercise.
- `master` branch should not contain completed solutions, only unit tests and
  starter code files.
- Do try to keep the tests in failing mode and not error mode, because if there
  is an error, the huge stack trace can be annoying. This means adding enough
  starter code to make the tests not giving errors.


## License

Copyright © 2021

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
