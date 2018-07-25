js.html5shiv
************

Introduction
============

This library packages `html5shiv`_ for `fanstatic`_.

.. _`fanstatic`: http://fanstatic.org
.. _`html5shiv`: https://github.com/aFarkas/html5shiv

This requires integration between your web framework and ``fanstatic``,
and making sure that the original resources (shipped in the ``resources``
directory in ``js.html5shiv``) are published to some URL.

Updating
========

The lateset version of the library can be downloaded using the following
commands::

    cd js/html5shiv/resources
    wget https://github.com/aFarkas/html5shiv/raw/master/src/html5shiv.js -O html5shiv.js
    wget https://github.com/aFarkas/html5shiv/raw/master/src/html5shiv-printshiv.js -O html5shiv-printshiv.js
    wget https://github.com/aFarkas/html5shiv/raw/master/dist/html5shiv.js -O html5shiv.min.js 
    wget https://github.com/aFarkas/html5shiv/raw/master/dist/html5shiv-printshiv.js -O html5shiv-printshiv.min.js

This will ensure the files are named correctly as the original library names
both the source and minified versions with the same filenames within its
repository.
