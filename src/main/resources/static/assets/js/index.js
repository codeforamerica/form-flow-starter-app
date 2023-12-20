'use strict';

window.uswdsPresent = true; // Indicate uswds.js has loaded in the DOM.

/**
 * USWDS packages
 */

// import banner from '@uswds/uswds/js/usa-banner';
// import characterCount from '@uswds/uswds/js/usa-character-count';
// import comboBox from '@uswds/uswds/js/usa-combo-box';
// import datePicker from '@uswds/uswds/js/usa-date-picker';
// import dateRangePicker from '@uswds/uswds/js/usa-date-range-picker';
// import fileInput from '@uswds/uswds/js/usa-file-input';
// import footer from '@uswds/uswds/js/usa-footer';
// import inPageNavigation from '@uswds/uswds/js/usa-in-page-navigation';
// import inputMask from '@uswds/uswds/js/usa-input-mask';
// import languageSelector from '@uswds/uswds/js/usa-language-selector';
// import modal from '@uswds/uswds/js/usa-modal';
// import password from '@uswds/uswds/js/_usa-password';
// import search from '@uswds/uswds/js/usa-search';
// import table from '@uswds/uswds/js/usa-table';
// import timePicker from '@uswds/uswds/js/usa-time-picker';

import accordion from '@uswds/uswds/js/usa-accordion';
import button from '@uswds/uswds/js/usa-button';
import navigation from '@uswds/uswds/js/usa-header';
import skipnav from '@uswds/uswds/js/usa-skipnav';
import tooltip from '@uswds/uswds/js/usa-tooltip';

/**
 * Code for America packages
 */

import Copy from '@codeforamerica/uswds/packages/cfa-copy/cfa-copy.js';
import Details from '@codeforamerica/uswds/packages/cfa-details/cfa-details.js';
import FollowUpQuestion
  from '@codeforamerica/uswds/packages/cfa-follow-up-question/cfa-follow-up-question.js';
import MaskDollars
  from '@codeforamerica/uswds/packages/cfa-mask/cfa-mask-dollars.js';
import MaskTel from '@codeforamerica/uswds/packages/cfa-mask/cfa-mask-tel.js';
import MaskSSN from '@codeforamerica/uswds/packages/cfa-mask/cfa-mask-ssn.js';
import UploadDocuments
  from '@codeforamerica/uswds/packages/cfa-upload-documents/cfa-upload-documents.js';

/**
 * Initialize USWDS and Code for America theme utilities and components
 */
new Copy();
new Details();
new FollowUpQuestion();
new MaskDollars();
new MaskTel();
new MaskSSN();

skipnav.on(document.body);
tooltip.on(document.body);
navigation.on(document.body);
accordion.on(document.body);
button.on(document.body);
