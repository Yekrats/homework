# Record-parsing and view demo

This is a demo of record reading and parsing. 

## Requirements

The app is currently uncompiled, and will require Leiningen to run. See https://github.com/technomancy/leiningen

## Installation

There is nothing to install. As this is an example for demonstration purposes, most of this is intended to be run from the Clojure REPL. However, a basic view can be created from the command line app. Give the app the path of any files you would like to view, with the full path in quotes.

### Example
Using Leiningen, the app can be run with the `lein run` command, then the file paths in quote marks, all separated by spaces, like so:

`lein run "./resources/comma-data.csv" "./resources/space-data.txt" "./resources/pipe-data.txt"`

The app should present the records from those three files sorted in reverse-email order.

## File Formats

The app should parse data from comma-separated lists, pipe-separated lists, or simple data parsed with spaces.

## Testing

Testing can be shown through Leiningen with `lein test`.

## Author

This has been created by Scott W. Starkey. For more information, contact me at yekrats@gmail.com

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
