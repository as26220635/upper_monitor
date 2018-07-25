from fanstatic import Library, Resource

library = Library('html5shiv', 'resources')

html5shiv = Resource(library, 'html5shiv.js', minified='html5shiv.min.js')

html5printshiv = Resource(library, 'html5shiv-printshiv.js', minified='html5shiv-printshiv.min.js')
