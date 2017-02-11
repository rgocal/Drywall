package sapphyx.gsd.com.drywall.development;

import android.app.ApplicationErrorReport;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.io.PrintWriter;
import java.io.StringWriter;

import sapphyx.gsd.com.drywall.R;

/**
 * Created by ry on 1/2/17.
 */

public class TheTeam extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    final CharSequence[] items = {"Crash", "Application Not Responding", "Battery Consumption", "Running Background Services"};

    public static TheTeam newInstance(int sectionNumber) {
        TheTeam fragment = new TheTeam();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public TheTeam() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.the_team, container, false);
        setHasOptionsMenu(true);

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.bug_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bugs:
                showReport();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showReport() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("Report Log");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Crash")) {
                    try {
                        int i = 3 / 0;
                    } catch (Exception e) {
                        ApplicationErrorReport report = new ApplicationErrorReport();
                        report.packageName = report.processName = getActivity()
                                .getPackageName();
                        report.time = System.currentTimeMillis();
                        report.type = ApplicationErrorReport.TYPE_CRASH;
                        report.systemApp = false;

                        ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
                        crash.exceptionClassName = e.getClass().getSimpleName();
                        crash.exceptionMessage = e.getMessage();

                        StringWriter writer = new StringWriter();
                        PrintWriter printer = new PrintWriter(writer);
                        e.printStackTrace(printer);

                        crash.stackTrace = writer.toString();

                        StackTraceElement stack = e.getStackTrace()[0];
                        crash.throwClassName = stack.getClassName();
                        crash.throwFileName = stack.getFileName();
                        crash.throwLineNumber = stack.getLineNumber();
                        crash.throwMethodName = stack.getMethodName();

                        report.crashInfo = crash;

                        Intent intent = new Intent(Intent.ACTION_APP_ERROR);
                        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
                        startActivity(intent);
                    }
                } else if (items[item].equals("Application Not Responding")) {
                    try {
                        int i = 3 / 0;
                    } catch (Exception e) {
                        ApplicationErrorReport report = new ApplicationErrorReport();
                        report.packageName = report.processName = getActivity()
                                .getPackageName();
                        report.time = System.currentTimeMillis();
                        report.type = ApplicationErrorReport.TYPE_ANR;
                        report.systemApp = false;

                        ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
                        crash.exceptionClassName = e.getClass().getSimpleName();
                        crash.exceptionMessage = e.getMessage();

                        StringWriter writer = new StringWriter();
                        PrintWriter printer = new PrintWriter(writer);
                        e.printStackTrace(printer);

                        crash.stackTrace = writer.toString();

                        StackTraceElement stack = e.getStackTrace()[0];
                        crash.throwClassName = stack.getClassName();
                        crash.throwFileName = stack.getFileName();
                        crash.throwLineNumber = stack.getLineNumber();
                        crash.throwMethodName = stack.getMethodName();

                        report.crashInfo = crash;

                        Intent intent = new Intent(Intent.ACTION_APP_ERROR);
                        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
                        startActivity(intent);
                    }
                } else if (items[item].equals("Battery Consumption")) {
                    try {
                        int i = 3 / 0;
                    } catch (Exception e) {
                        ApplicationErrorReport report = new ApplicationErrorReport();
                        report.packageName = report.processName = getActivity()
                                .getPackageName();
                        report.time = System.currentTimeMillis();
                        report.type = ApplicationErrorReport.TYPE_BATTERY;
                        report.systemApp = false;

                        ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
                        crash.exceptionClassName = e.getClass().getSimpleName();
                        crash.exceptionMessage = e.getMessage();

                        StringWriter writer = new StringWriter();
                        PrintWriter printer = new PrintWriter(writer);
                        e.printStackTrace(printer);

                        crash.stackTrace = writer.toString();

                        StackTraceElement stack = e.getStackTrace()[0];
                        crash.throwClassName = stack.getClassName();
                        crash.throwFileName = stack.getFileName();
                        crash.throwLineNumber = stack.getLineNumber();
                        crash.throwMethodName = stack.getMethodName();

                        report.crashInfo = crash;

                        Intent intent = new Intent(Intent.ACTION_APP_ERROR);
                        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
                        startActivity(intent);
                    }
                } else if (items[item].equals("Running Background Services")) {
                    try {
                        int i = 3 / 0;
                    } catch (Exception e) {
                        ApplicationErrorReport report = new ApplicationErrorReport();
                        report.packageName = report.processName = getActivity()
                                .getPackageName();
                        report.time = System.currentTimeMillis();
                        report.type = ApplicationErrorReport.TYPE_RUNNING_SERVICE;
                        report.systemApp = false;

                        ApplicationErrorReport.CrashInfo crash = new ApplicationErrorReport.CrashInfo();
                        crash.exceptionClassName = e.getClass().getSimpleName();
                        crash.exceptionMessage = e.getMessage();

                        StringWriter writer = new StringWriter();
                        PrintWriter printer = new PrintWriter(writer);
                        e.printStackTrace(printer);

                        crash.stackTrace = writer.toString();

                        StackTraceElement stack = e.getStackTrace()[0];
                        crash.throwClassName = stack.getClassName();
                        crash.throwFileName = stack.getFileName();
                        crash.throwLineNumber = stack.getLineNumber();
                        crash.throwMethodName = stack.getMethodName();

                        report.crashInfo = crash;

                        Intent intent = new Intent(Intent.ACTION_APP_ERROR);
                        intent.putExtra(Intent.EXTRA_BUG_REPORT, report);
                        startActivity(intent);
                    }
                }
            }
        });
        builder.show();
    }
}
