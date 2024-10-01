(defproject olho-de-tandera "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url  "https://www.eclipse.org/legal/epl-2.0/"}

  :plugins [[com.github.clojure-lsp/lein-clojure-lsp "1.4.2"]]

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [net.clojars.macielti/common-clj "30.63.70" :exclusions [datalevin]]]

  :aliases {"clean-ns"     ["clojure-lsp" "clean-ns" "--dry"] ;; check if namespaces are clean
            "format"       ["clojure-lsp" "format" "--dry"] ;; check if namespaces are formatted
            "diagnostics"  ["clojure-lsp" "diagnostics"]    ;; check if project has any diagnostics (clj-kondo findings)
            "lint"         ["do" ["clean-ns"] ["format"] ["diagnostics"]] ;; check all above
            "clean-ns-fix" ["clojure-lsp" "clean-ns"]       ;; Fix namespaces not clean
            "format-fix"   ["clojure-lsp" "format"]         ;; Fix namespaces not formatted
            "lint-fix"     ["do" ["clean-ns-fix"] ["format-fix"]]} ;; Fix both

  :injections [(require 'hashp.core)]

  :resource-paths ["resources" "test/resources/"]

  :test-paths ["test/unit" "test/integration" "test/helpers"]

  :repl-options {:init-ns olho-de-tandera.components}

  :src-dirs ["src"]

  :aot :all

  :main olho-de-tandera.components)
